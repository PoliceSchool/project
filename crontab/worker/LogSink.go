package worker

import (
	"context"
	"crontab/common"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"time"
)

// mongodb 存储日志
type LogSink struct {
	client         *mongo.Client
	logCollection  *mongo.Collection
	logChan        chan *common.JobLog
	autoCommitChan chan *common.LogBatch
}

var (
	G_logSink *LogSink
)

func (logSink *LogSink) saveLogs(batch *common.LogBatch) {
	logSink.logCollection.InsertMany(context.TODO(), batch.Logs)
}

// 日志存储协程
func (logSink *LogSink) writeLoop() {
	var (
		log          *common.JobLog
		logBatch     *common.LogBatch // 当期的批次
		commitTimer  *time.Timer
		timeoutBatch *common.LogBatch // 超时的批次
	)

	for {
		select {
		case log = <-logSink.logChan:
			// 把这条log写到mongodb中
			// 每次插入需要等待mongodb的一次请求往返，耗时可能因为网络慢话费比较长的时间，所以需要放在一个数组里面等待批量插入
			if logBatch == nil {
				logBatch = &common.LogBatch{}
				// 让这个批次超时自动提交(给1秒的时间)
				commitTimer = time.AfterFunc(time.Duration(G_config.JobLogCommitTimeout)*time.Millisecond,
					// 因为外边的writeLoop随时可能将logBatch置为nil，所以不能直接引用外边的logBatch
					// 所以需要将logBatch作为一个参数传进来，所以里面就可以随意引用了
					func(batch *common.LogBatch) func() {
						return func() {
							// 因为这个超时函数是在另一个协程里面运行的，如果在这里直接提交batch会有并发问题
							// 因为外边的writeLoop也在操作batch
							// 所以为了避免并发操作，不在这里提交batch，这里只是向外边的writeLoop发出超时通知，由writeLoop去提交，形成串行化处理
							logSink.autoCommitChan <- batch
						}
					}(logBatch),
				)
			}
			// 把新的日志追加到批次中
			logBatch.Logs = append(logBatch.Logs, log)

			// 如果批次满了，就立即发送
			if len(logBatch.Logs) >= G_config.JobLogBatchSize {
				// 发送日志
				logSink.saveLogs(logBatch)
				// 清空logBatch
				logBatch = nil
				// 取消定时器
				commitTimer.Stop()
			}
		case timeoutBatch = <-logSink.autoCommitChan: // 过期的批次
			// 判断过期批次是否仍旧是当前的批次
			if timeoutBatch != logBatch {
				continue // 跳过已经被提交的批次
			}
			// 把批次写入到mongo中
			logSink.saveLogs(timeoutBatch)
			logBatch = nil
		}
	}
}

func InitLogSink() (err error) {
	var (
		client *mongo.Client
	)

	// 建立mongodb连接
	if client, err = mongo.Connect(context.TODO(), options.Client().ApplyURI(G_config.MongodbUri)); err != nil {
		return
	}

	// 选择db和Collection
	G_logSink = &LogSink{
		client:         client,
		logCollection:  client.Database("cron").Collection("log"),
		logChan:        make(chan *common.JobLog, 1000),
		autoCommitChan: make(chan *common.LogBatch, 1000),
	}

	// 启动一个mongodb的处理协程
	go G_logSink.writeLoop()
	return
}

// 发送日志
func (logSink *LogSink) Append(jobLog *common.JobLog) {
	select {
	case logSink.logChan <- jobLog:
	default:
		// 队列满了就丢弃
	}
}
