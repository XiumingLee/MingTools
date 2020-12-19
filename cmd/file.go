package cmd

// 文件操作相关
import (
	"fmt"
	"github.com/spf13/cobra"
)

func init() {
	RootCmd.AddCommand(fileCmd)
}

var fileCmd = &cobra.Command{
	Use:   "file",
	Short: "file",
	Long:  "文件操作.",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("我是file操作！")
	},
}
