# MingTools
基于Spring Boot、JavaFX的桌面端工具集合，不断完善。

> 适用于 `Mac`、`Windows`和`Linux`。要求Java11及以上版本
>
> Gitee：https://gitee.com/xiuminglee/MingTools
>
> GitHub：https://github.com/XiumingLee/MingTools

## 功能简介

- **七牛云**
  - 图片一键上传 `Ctrl + Shift + V`
  - 图片删除
- **OCR和翻译**
  - 百度图片OCR识别，图片转文字 `Ctrl + Shift + T`
  - 百度翻译
- **文件处理**
  - 批量修改文件名称，取出文件中的指定字符串
  - 将文件名修改为汉语拼音。

## 功能预览

![](docs/img/qiniu.png?raw=true)
![](docs/img/ocr_trans.jpg?raw=true)
![](docs/img/file.png?raw=true)

## 功能使用

> 打包完成后，目录是这样的。

```shell
.
├── conf
│   └── application.yaml   ## 配置文件
├── lib
│   └── ming-tools.jar
├── run-jre.bat
├── run-jre.sh
├── run.bat  ## windows启动脚本
└── run.sh   ## mac、linux启动脚本
```

> `注意：`
>
> `run-jre.bat`、`run-jre.sh`是当你的电脑上`没有JDK环境时`，可以使用定制的`JRE环境`运行。加入`JRE环境`的目录是这样的
>
> ```shell
> .
> ├── conf
> │   └── application.yaml   ## 配置文件
> ├── lib
> │   └── ming-tools.jar
> ├── jre       ## 这里多了一个jre的目录。
> ├── run-jre.bat
> ├── run-jre.sh
> ├── run.bat  ## windows启动脚本
> └── run.sh   ## mac、linux启动脚本
> ```
>
> 需要定制JRE的同学可以到`Q群：962917903`获取。当然你也可以自己定制或者直接安装JDK.

### 1、配置文件

> 在使用`七牛云`和`OCR和翻译`功能时，需要到相应的网站获取自己的应用key和秘钥。

- #### 七牛云

创建存储空间：https://developer.qiniu.com/kodo/manual/1233/console-quickstart

自己的秘钥：https://portal.qiniu.com/user/key

- #### OCR

在以下网站创建应用，并获取`API Key` 和`Secret Key`

https://console.bce.baidu.com/ai/#/ai/ocr/app/list

- #### 翻译

在以下网站获取`APP ID`和`秘钥`

https://fanyi-api.baidu.com/api/trans/product/desktop?req=developer

将以上key和秘钥填写到`application.yaml`配置文件中

```yaml
ming:
  tools:
    baidu:
      access-ocr-key: Your 百度OCR accessKey
      secret-ocr-key: Your 百度OCR secretKey
      access-trans-key: Your 百度翻译 accessKey  APP ID
      secret-trans-key: Your 百度翻译 secretKey
    qiniu:
      access-key: Your 七牛云 accessKey
      secret-key: Your 七牛云 secretKey
      bucket-name: 要上传到的空间
      file-path-prefix: 文件地址前缀，cdn地址
```

> `注意：`不使用的功能，可以不填写为自己的key和秘钥，但是不要删除或置为空。

### 2、启动软件

> - **Mac/Linux**
>
> ```shell
> sh run.sh
> 
> ## sh run-jre.sh
> ```
>
> - #### windows
>
> 双击`run.bat`即可

## 下载地址

[下载地址](https://github.com/XiumingLee/MingTools/releases)

