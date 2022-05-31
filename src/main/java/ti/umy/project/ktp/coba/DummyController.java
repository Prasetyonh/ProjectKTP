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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Acer
 */
@Controller
public class DummyController {

    DummyJpaController dummyController = new DummyJpaController();
    List<Dummy> data = new ArrayList<>();

    @RequestMapping("/read")

    public String getDummy(Model model) {
        try {
            data = dummyController.findDummyEntities();
        } catch (Exception e) {
        }
        model.addAttribute("data", data);
        return "dummy";

    }

    @RequestMapping("/create")
    public String createDummy() {
        return "dummy/create";
    }

    @PostMapping(value = "/newdata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String newDummyData(@RequestParam("gambar") MultipartFile file, HttpServletRequest request)
            throws ParseException, Exception {
        Dummy dummy = new Dummy();

//        int id = Integer.parseInt(r.getParameter("id"));
        String nama = request.getParameter("nama");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("tanggal"));
        byte[] img = file.getBytes();
//        d.setId(id);
        dummy.setNama(nama);
        dummy.setTanggal(date);
        dummy.setGambar(img);

        dummyController.create(dummy);
        return "redirect:/read";
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET, produces = {
        MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
    })
    public ResponseEntity<byte[]> getImg(@RequestParam("id") int id) throws Exception {
        Dummy dummy = dummyController.findDummy(id);
        byte[] img = dummy.getGambar();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
    }

    @GetMapping("/delete/{id}")

    public String deleteDummy(@PathVariable("id") int id) throws Exception {
        dummyController.destroy(id);
        return "redirect:/read";
    }

    @RequestMapping("/edit/{id}")
    public String updateDummy(@PathVariable("id") int id, Model model) throws Exception {
        Dummy dummy = dummyController.findDummy(id);
        model.addAttribute("data", dummy);
        return "dummy/edit";
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateDummyData(@RequestParam("gambar") MultipartFile file, HttpServletRequest request)
            throws ParseException, Exception {
        Dummy dummy = new Dummy();
        String nama = request.getParameter("nama");
        int id = Integer.parseInt(request.getParameter("id"));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("tanggal"));
        byte[] img = file.getBytes();
        dummy.setId(id);
        dummy.setNama(nama);
        dummy.setTanggal(date);
        dummy.setGambar(img);

        dummyController.edit(dummy);
        return "redirect:/read";
    }

}
