package localquest.auth.entity;


import localquest.data.hint.entity.TextHintEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "user_found_hints")
    private Set<TextHintEntity> foundHints;

    @ManyToMany
    @JoinTable(name = "user_unlocked_hints")
    private Set<TextHintEntity> unlockedHints;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<TextHintEntity> getFoundHints() {
        return foundHints;
    }

    public void setFoundHints(Set<TextHintEntity> foundHints) {
        this.foundHints = foundHints;
    }

    public Set<TextHintEntity> getUnlockedHints() {
        return unlockedHints;
    }

    public void setUnlockedHints(Set<TextHintEntity> unlockedHints) {
        this.unlockedHints = unlockedHints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
