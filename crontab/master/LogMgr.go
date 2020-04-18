package master

import (
	"context"
	"crontab/common"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type LogMgr struct {
	client        *mongo.Client
	logCollection *mongo.Collection
}

var (
	G_logMgr *LogMgr
)

func InitLogMgr() (err error) {
	var (
		client *mongo.Client
	)

	// 建立mongodb连接
	if client, err = mongo.Connect(context.TODO(), options.Client().ApplyURI(G_config.MongodbUri)); err != nil {
		return
	}

	G_logMgr = &LogMgr{
		client:        client,
		logCollection: client.Database("cron").Collection("log"),
	}
	return
}

// 查看任务日志
func (logMgr *LogMgr) ListLog(name string, skip int, limit int) (logArr []*common.JobLog, err error) {
	var (
		filter  *common.JobLogFilter
		logSort *common.SortLogByStartTime
		cursor  *mongo.Cursor
		jobLog  *common.JobLog
	)
	logArr = make([]*common.JobLog, 0)

	// 过滤条件
	filter = &common.JobLogFilter{JobName: name}

	// 按照任务开始时间排序
	logSort = &common.SortLogByStartTime{SortOrder: -1}

	// 查询
	if cursor, err = logMgr.logCollection.Find(context.TODO(), filter, options.Find().SetSkip(int64(skip)).SetLimit(int64(limit)).SetSort(logSort)); err != nil {
		return
	}
	// 延迟释放游标
	defer cursor.Close(context.TODO())

	for cursor.Next(context.TODO()) {
		jobLog = &common.JobLog{
			JobName:      "",
			Command:      "",
			Err:          "",
			Output:       "",
			PlanTime:     0,
			ScheduleTime: 0,
			StartTime:    0,
			EndTime:      0,
		}

		// 反序列化BSON
		if err = cursor.Decode(jobLog); err != nil {
			continue //有日志不合法
		}
		logArr = append(logArr, jobLog)
	}
	return
}
