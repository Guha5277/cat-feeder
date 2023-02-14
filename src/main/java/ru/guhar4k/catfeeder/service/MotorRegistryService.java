package ru.guhar4k.catfeeder.service;

import org.springframework.stereotype.Service;
import ru.guhar4k.catfeeder.model.MotorServiceInfo;
import ru.guhar4k.catfeeder.service.motor.MotorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MotorRegistryService {
    private final static AtomicInteger counter = new AtomicInteger(1);
    private final static Map<Integer, MotorServiceInfo> motorsMap = new HashMap<>();

    public void registerMotorService(MotorService motorService) {
        int motorId = counter.getAndIncrement();
        motorsMap.put(motorId, new MotorServiceInfo(motorId, motorService, motorService.getName()));
    }

    public MotorService getMotorServiceById(int id) {
        return motorsMap.get(id).getMotorService();
    }

    public MotorServiceInfo getMotorServiceInfoById(int id) {
        return motorsMap.get(id);
    }

    public List<MotorServiceInfo> getAllMotorServicesInfos() {
        return new ArrayList<>(motorsMap.values());
    }

}
