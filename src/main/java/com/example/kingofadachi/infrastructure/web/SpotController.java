package com.example.kingofadachi.infrastructure.web;

import com.example.kingofadachi.domain.model.Spot;
import com.example.kingofadachi.domain.repository.SpotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    private final SpotRepository spotRepository;

    public SpotController(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @PostMapping
    public ResponseEntity<Spot> createSpot(@RequestBody Spot spot) {
        try {
            Spot savedSpot = spotRepository.save(spot);
            return new ResponseEntity<>(savedSpot, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 指定されたIDのスポット情報を取得します。
     *
     * @param id スポットID
     * @return 見つかった場合はスポット情報、見つからない場合は 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Spot> getSpotById(@PathVariable Long id) {
        Optional<Spot> spotData = spotRepository.findById(id);
        return spotData.map(spot -> new ResponseEntity<>(spot, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * すべてのスポット情報を取得します。
     *
     * @return スポット情報のリスト
     */
    @GetMapping
    public ResponseEntity<List<Spot>> getAllSpots() {
        try {
            List<Spot> spots = spotRepository.findAll();
            if (spots.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // データがない場合は 204 No Content
            }
            return new ResponseEntity<>(spots, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
