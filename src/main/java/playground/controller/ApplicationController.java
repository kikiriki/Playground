package playground.controller;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.model.SimpleEntity;
import playground.model.SimpleEntityRepo;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {

  @PostConstruct
  public void setUp() {
    mongoTemplate.indexOps(SimpleEntity.class).ensureIndex( new GeospatialIndex("location") );
  }

  private SimpleEntityRepo simpleEntityRepo;
  private MongoTemplate mongoTemplate;

  public ApplicationController(SimpleEntityRepo simpleEntityRepo, MongoTemplate mongoTemplate) {
    this.simpleEntityRepo = simpleEntityRepo;
    this.mongoTemplate = mongoTemplate;
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity insert(@RequestParam double lat, @RequestParam double lon) {

    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setTitle("test");
    simpleEntity.setEmail("test@test.co");
    double[] coordinates = new double[] { lon, lat };
    simpleEntity.setLocation(coordinates);

    SimpleEntity save = simpleEntityRepo.save(simpleEntity);

    return new ResponseEntity<>(save, HttpStatus.OK);
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ResponseEntity list(@RequestParam double lat, @RequestParam double lon) {

    Point point = new Point(lon, lat);

    List<SimpleEntity> all = mongoTemplate.find(new Query(Criteria.where("location").near(point).maxDistance(0.01)),
        SimpleEntity.class);
    return new ResponseEntity(all, HttpStatus.OK);
  }

  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  public ResponseEntity listAll() {

    List<SimpleEntity> all = simpleEntityRepo.findAll();
    return new ResponseEntity(all, HttpStatus.OK);
  }

}