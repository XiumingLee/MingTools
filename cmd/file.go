package cmd

// 文件操作相关
import (
	"github.com/spf13/cobra"
	"xiuminglee.cn/MingTools/util/config"
	"xiuminglee.cn/MingTools/util/file"
)

var fileConfig = config.FileConfig{}

func init() {
	fileCmd.Flags().BoolVar(&fileConfig.Rename, "rename", false, "操作类型：重命名")
	fileCmd.Flags().StringVar(&fileConfig.SrcDir, "src-dir", "", "要操作的文件夹")
	fileCmd.Flags().StringVar(&fileConfig.RemoveStr, "remove_str", "", "要删除文件名中的字符串")

	RootCmd.AddCommand(fileCmd)
}

var fileCmd = &cobra.Command{
	Use:   "file",
	Short: "file",
	Long:  "文件操作.",
	Run: func(cmd *cobra.Command, args []string) {
		// 删除文件名中的特定字符串
		if fileConfig.Rename {
			file.RenameFiles(fileConfig.SrcDir, fileConfig.RemoveStr)
		} else {
			cmd.Help()
		}
	},
}
