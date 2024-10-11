package com.naver.kiosk.kiosk;

import com.naver.kiosk.store.Store;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kiosks")
public class KioskController {

    @GetMapping
    public List<Kiosk> getAllKiosks() {
        return Utils.kiosks;
    }

    @PostMapping
    public Kiosk saveKiosk(@RequestBody KioskRequest request) {
        Kiosk kiosk = request.toKiosk();
        Utils.kiosks.add(kiosk);
        return kiosk;
    }

    @DeleteMapping("/{kioskId}")
    public void deleteKiosk(@PathVariable("kioskId") int id) {
        Kiosk kioskById = getKioskById(id);
        if (kioskById != null) {
            Utils.kiosks.remove(kioskById);
        }
    }

    private Kiosk getKioskById(int id) {
        return Utils.kiosks.stream()
                .filter(kiosk -> kiosk.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
