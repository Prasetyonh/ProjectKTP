/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package ti.umy.project.ktp.coba;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Acer
 */
@Controller
public class DummyController {
        
        DummyJpaController dummyController =new DummyJpaController();
        List<Dummy> data = new ArrayList<>();
    
    @RequestMapping("/read")
    @ResponseBody
    public List<Dummy> getDummy() {
        try {
            data = dummyController.findDummyEntities();
        } catch (Exception e) {
        }
        return data;
    
    }
        @RequestMapping("/create")
        public String createDummy(){

            return "dummy/create";
        }
        
        @PostMapping(value="/newData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String newDummy(@RequestParam("gambar") MultipartFile file, HttpServletRequest data) throws ParseException, Exception{
        
        Dummy dumData = new Dummy();
        
        String id = data.getParameter("id");
        int iid = Integer.parseInt(id);
        
        String tanggal = data.getParameter("tanggal");
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(tanggal);
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        byte[] image = file.getBytes();
        
        dumData.setId(iid);
        dumData.setTanggal(date);
        dumData.setGambar(image);
        
        dummyController.create(dumData);

            return "dummy/create";
        }
    
}
