package localquest.auth.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserEntity user;

    @Column(unique = true)
    private String token = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionEntity that = (SessionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
