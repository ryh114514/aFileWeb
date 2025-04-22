package ryh.thesrcfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ryh.thesrcfinder.mapper.DBreflasher;
import ryh.thesrcfinder.mapper.srcSelecter;
import ryh.thesrcfinder.pojo.information;
import ryh.thesrcfinder.pojo.result;
import ryh.thesrcfinder.service.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class flash {
    private List<information>data = new ArrayList<>();
    private String s="";
    @Autowired
    private srcSelecter srcSelecter;
    @Autowired
    private DBreflasher PathMapper;
    @Autowired
    private scanner Scanner;
    @Transactional
    @RequestMapping(value = "/flash",method = RequestMethod.POST)
     public result hello( String src,Integer pagesize) throws IOException {
        if(src.equals(s))
        {
            return result.error("重复刷新");
        }

        data = Scanner.scan(src);

        if(data.isEmpty()){
            return result.error("数据不存在");
        }
        s=src;
        PathMapper.delete();
        PathMapper.reflash(data);
        return result.success(data.subList(0,Math.min(pagesize,data.size())),data.size());
    }
    @Transactional
    @RequestMapping(value = "/ini",method = RequestMethod.GET)
    public result ini() throws IOException {
        return result.success(Scanner.getTheType(),null);
    }


    @Transactional
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public result hello(String like,String size,String type,Integer pagesize) throws IOException {
        Double Size;
        try {
            Size=Double.parseDouble(size);
        }
        catch (Exception e){
            Size=null;
        }
        data = srcSelecter.select(like,Size,type,pagesize);

        return  result.success(data.subList(0,Math.min(pagesize,data.size())),data.size());
    }
    @Transactional
    @RequestMapping(value = "/changePage",method = RequestMethod.POST)
    public result changePage(Integer pagesize,Integer currentPage) throws IOException {
        return  result.success(data.subList(pagesize*(currentPage-1),Math.min(pagesize*(currentPage),data.size())),data.size());
    }
}
