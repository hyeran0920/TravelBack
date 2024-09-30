package filminkorea.fik.repositories;

import filminkorea.fik.entities.Layer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayerRepository extends JpaRepository<Layer, Integer> {
    // 기본적인 CRUD 작업은 JpaRepository가 제공
    // 필요 시 커스텀 쿼리 메서드를 추가할 수 있습니다.
    // 분류 => query
}
