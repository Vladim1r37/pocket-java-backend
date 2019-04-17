//package ru.geekbrains.pocket.backend.domain.db;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.TypeAlias;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
////this class for Spring Security
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Document(collection = "roles")
//@TypeAlias("roles")
//public class Role {
//    @Id
//    private ObjectId id;
//
//    @NotNull
//    @NotEmpty
//    @Indexed(unique = true)
//    private String name;
//
//    @DBRef
//    @NotNull
//    @Valid
//    @JsonIgnore
//    @Field(value = "privileges")
//    private List<Privilege> privileges;
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Role{" +
//                "id=" + id +
//                ", name=" + name +
//                '}';
//    }
//}
