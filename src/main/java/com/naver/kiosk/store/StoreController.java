package com.naver.kiosk.store;

import jdk.jshell.execution.Util;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StoreController {
    @GetMapping
    public List<Store> getAllStores() {
        return Utils.stores;
    }

    @GetMapping("/{store-id}")
    public Store getStoreById(@PathVariable(value = "store-id") int id) {
//        Integer id =10_000;
        Optional<Store> first = Utils.stores
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        if (first.isEmpty()) throw new StoreNotFound(id);
        return first.get();
    }

    @PostMapping
    public Store saveStore(
            @RequestBody StoreRequest request
    ) {
        Store store = request.toStore();
        Utils.stores.add(store);
        return store;
    }

    @DeleteMapping("/{store-id}")
    public void deleteStore(@PathVariable(value = "store-id") int id) {
        Store storeById = getStoreById(id);
        Utils.stores.remove(storeById);
    }

    @PutMapping("/{store-id}")
    public Store updateStore(
            @PathVariable(value = "store-id") int id,
            @RequestBody StoreRequest request
    ) {
        return getStoreById(id).update(request);
    }
}