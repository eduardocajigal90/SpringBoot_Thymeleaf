package com.quiox.test.projection;

import com.quiox.test.model.TypeExampleEntity;
import java.util.UUID;

public record ExampleEntityProjection(
        UUID id,
        String code,
        String name,
        TypeExampleEntity type){

}
