package asik.propensik.trainnas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pelatihan_trainee")
@JsonIgnoreProperties(value={"id_pelatihan","id"}, allowSetters = true)
public class PelatihanTrainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelatihanTrainee;

    @NotNull
    @Column(name = "stok", nullable = false, length = 255)
    private int stok;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name =  "id_pelatihan",  referencedColumnName = "idPelatihan")
    private Pelatihan idPelatihan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name =  "id",  referencedColumnName = "id")
    private UserModel id;
}
