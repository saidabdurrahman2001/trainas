package asik.propensik.trainnas.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pelatihan")
@SQLDelete(sql = "UPDATE pelatihan SET is_deleted = true WHERE id_pelatihan=?")
@Where(clause = "is_deleted=false")
public class Pelatihan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelatihan;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String namaPelatihan;

    @NotNull
    @Column(name = "tipe", nullable = false)
    private String tipe;

    @Column(name = "penyelenggaraan")
    private String penyelenggaraan;

    @NotNull
    @Column(name = "tempat", nullable = false)
    private String tempat;

    @NotNull
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "narahubung", nullable = false)
    private String narahubung;

    @NotNull
    @Column(name = "tanggal_pelatihan", nullable = false)
    private Date tanggal;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = Boolean.FALSE;

    @Column(name = "status")
    private int statusApproval = 1; // 1 request, 2 approved, 3 rejected, 4 requested delete

    @Column(name = "jumlahPendaftar")
    private int jumlahPendaftar = 0;

    @Column(name = "komentar")
    private String komentar;

    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime waktuPembuatan = LocalDateTime.now();

    // @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private List<UserModel> userRole;

    @OneToMany(mappedBy = "idPelatihan", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JsonIgnore
    private List<PelatihanTrainee> listPelatihanTrainee = new ArrayList<>();

    @OneToMany(mappedBy = "pelatihan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Testimoni> testimoni = new ArrayList<>();
}
