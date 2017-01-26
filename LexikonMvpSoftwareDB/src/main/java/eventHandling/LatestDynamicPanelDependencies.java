package eventHandling;

import java.util.List;

import model.Specialty;
import model.TechnicalTerm;
import model.Translations;

public class LatestDynamicPanelDependencies {
	List<Translations> searchTechnicalTermsList;
	List<Specialty> specialtyList;
	List<TechnicalTerm> technicalTermsOfSpecialtyList;
	
	public List<Translations> getSearchTechnicalTermsList() {
		return searchTechnicalTermsList;
	}
	public void setSearchTechnicalTermsList(List<Translations> searchTechnicalTermsList) {
		this.searchTechnicalTermsList = searchTechnicalTermsList;
	}
	public List<Specialty> getSpecialtyList() {
		return specialtyList;
	}
	public void setSpecialtyList(List<Specialty> specialtyList) {
		this.specialtyList = specialtyList;
	}
	public List<TechnicalTerm> getTechnicalTermsOfSpecialtyList() {
		return technicalTermsOfSpecialtyList;
	}
	public void setTechnicalTermsOfSpecialtyList(List<TechnicalTerm> technicalTermsOfSpecialtyList) {
		this.technicalTermsOfSpecialtyList = technicalTermsOfSpecialtyList;
	}
}
