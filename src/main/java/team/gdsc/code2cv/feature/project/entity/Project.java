package team.gdsc.code2cv.feature.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	public String getRepo(){
		return "repo";
	}

	public String getOwner(){
		return "owner";
	}
}
