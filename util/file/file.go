package file

import (
	"container/list"
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
)

type PathInfo struct {
	ParentPath string
	FileIno    os.FileInfo
}

// region 删除文件名中的特定字符串
// 删除文件名中的特定字符串
func RenameFiles(dirname string, removeStr string) {
	list := getAllFile(dirname)
	for i := list.Front(); i != nil; i = i.Next() {
		// 强制类型转换
		filePathInfo := i.Value.(PathInfo)
		removeStrFromName(filePathInfo, removeStr)
	}
}
func getAllFile(dirname string) *list.List {
	fileList := list.New()
	getFileToList(dirname, fileList)
	return fileList
}
func getFileToList(dirname string, fileList *list.List) {
	fs, _ := ioutil.ReadDir(dirname)
	for _, v := range fs {
		if v.IsDir() {
			sonDirname := dirname + string(filepath.Separator) + v.Name()
			getFileToList(sonDirname, fileList)
		} else {
			filePathInfo := PathInfo{dirname, v}
			fileList.PushBack(filePathInfo)
		}
	}
}
func removeStrFromName(filePathinfo PathInfo, str string) {
	fileName := filePathinfo.FileIno.Name()
	if strings.Contains(fileName, str) {
		oldName := filePathinfo.ParentPath + string(filepath.Separator) + fileName
		newFileName := strings.ReplaceAll(fileName, str, "")
		newName := filePathinfo.ParentPath + string(filepath.Separator) + newFileName
		os.Rename(oldName, newName)
	}
}

// endregion 删除文件名中的特定字符串
