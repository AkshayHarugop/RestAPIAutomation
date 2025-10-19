package pojo;

import java.util.List;

public class courses {
	private List<webAutomation> webAutomation;
	public List<webAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<webAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<api> getApi() {
		return api;
	}
	public void setApi(List<api> api) {
		this.api = api;
	}
	public List<mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<mobile> mobile) {
		this.mobile = mobile;
	}
	private List<api> api;
	private List<mobile> mobile;
	
	
	
}
