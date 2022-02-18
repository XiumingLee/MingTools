/**   系统中添加到左侧菜单栏的路由,更全的格式参阅`/common/mock/data.js`中的数据  */
export const Routers = [
    {
        "name": "Tool",
        "path": "/tool",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "meta": {
            "title": "工具",
            "icon": "tool",
            "noCache": false,
            "link": null
        },
        "children": [
            {
                "name": "File",
                "path": "file",
                "hidden": false,
                "component": "/views/tool/file/index.js",
                "meta": {
                    "title": "文件工具",
                    "icon": "file",
                    "noCache": false,
                    "link": null
                }
            },
        ]
    },
]

