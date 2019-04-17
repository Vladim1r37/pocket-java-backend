//package ru.geekbrains.pocket.backend.domain.db;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.TypeAlias;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Document(collection = "privileges")
//@TypeAlias("privileges")
//public class Privilege {
//
//    @Id
//    private ObjectId id;
//
//    private String name;
//
//    public Privilege(final String name) {
//        super();
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
//        return builder.toString();
//    }
//
////    @Override
////    public int hashCode() {
////        final int prime = 31;
////        int result = 1;
////        result = prime * result + ((name == null) ? 0 : name.hashCode());
////        return result;
////    }
////
////    @Override
////    public boolean equals(Object obj) {
////        if (this == obj)
////            return true;
////        if (obj == null)
////            return false;
////        if (getClass() != obj.getClass())
////            return false;
////        Privilege other = (Privilege) obj;
////        if (name == null) {
////            if (other.name != null)
////                return false;
////        } else if (!name.equals(other.name))
////            return false;
////        return true;
////    }
//
//}
