package com.example.demo.controller;

import com.example.demo.model.Range;
import com.example.demo.model.Sensor;
import com.example.demo.model.Type;
import com.example.demo.model.Unit;
import com.example.demo.model.dto.SensorDto;
import com.example.demo.model.dto.SensorDtoUpdate;
import com.example.demo.service.SensorService;
import com.example.demo.util.SensorConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/test")
public class MyRestController {
    private final SensorService service;

    @Autowired
    public MyRestController(SensorService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Sensor> allSensors() {
        return service.allSensors();
    }

/*    @GetMapping("/sensor/{id}")
    @ResponseBody
    public Sensor findById(@PathVariable long id){
        return service.findById(id);
    }*/

    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/admin/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor add(@RequestBody SensorDto sensorDto) {
        Sensor sensor = SensorConvertor.convertToSensor(sensorDto);
        service.save(sensor);;
        return sensor;
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @PutMapping("/admin/update")
    public void update(@RequestBody SensorDtoUpdate SensorDtoUpdate) {
        Sensor sensor = SensorConvertor.convertToSensor(SensorDtoUpdate);
        service.update(sensor);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @DeleteMapping("/admin/delete")
    public void delete(@RequestBody SensorDtoUpdate sensorDto) {
        Sensor sensor = SensorConvertor.convertToSensor(sensorDto);
        service.delete(sensor.getId());
    }

}
