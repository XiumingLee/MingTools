package cn.xiuminglee.tools;

import cn.xiuminglee.tools.modules.word.biz.BaiduService;

//@SpringBootTest
class MingToolsApplicationTests {
    //@Autowired
    private BaiduService baiduService;

    //@Test
    void contextLoads() throws Exception {
        String query = "高度600米";
        String transResult = baiduService.getTransResult(query, "auto", "en");
        System.out.println(transResult);
    }

}
