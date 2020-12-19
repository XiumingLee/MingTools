package config

type FileOperationType struct {
}
type FileConfig struct {
	// 类型
	// 重命名
	Rename bool `json:"rename"`
	//basic config
	SrcDir string `json:"src_dir"`

	//删除文件名的指定字符串
	RemoveStr string `json:"remove_str"`
}
