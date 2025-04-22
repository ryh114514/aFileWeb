package ryh.thesrcfinder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import ryh.thesrcfinder.pojo.information;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Component("Scanner")
public class scanner {
    public scanner() throws IOException {
    }

    public  List<String> getTheType() throws IOException {

        return type;
    }
    public List<information> scan(String src) throws IOException {


       data.clear();
        File srcn = new File(pre+src);
        findHelper(srcn,src);
       return data;
    }
    private void findHelper(File file,String pre){

        String tmp,tmp1;
        boolean is;
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                is = false;
                tmp = f.getName();

                int lastDotIndex = tmp.lastIndexOf('.');
                tmp1= tmp.substring(lastDotIndex + 1);
                for(String s:type){
                    if (tmp1.equals(s)) {
                        is = true;
                        break;
                    }
                }
                if (is) {
                    information info=new information();
                    info.setSize((double) f.length() / 1024 / 1024);
                    info.setSrc(path + pre +"/"+ tmp);
                    info.setName(tmp);
                    info.setType(tmp1);
                    data.add(info);

                }
            } else {
                findHelper(f, pre + "/" + f.getName());
            }
        }
    }

    private ObjectMapper mapper = new ObjectMapper();
    private List<information> data=new ArrayList<>();
    private List<String> type = mapper.readValue(
            new File(".\\config\\type.json"),
            new TypeReference<List<String>>() {}
    );
    private String path=mapper.readValue(
               new File(".\\config\\path.json"),
               new TypeReference<String>() {}
       );
    private String pre=mapper.readValue(
            new File(".\\config\\pre.json"),
            new TypeReference<String>() {}
    );
}
