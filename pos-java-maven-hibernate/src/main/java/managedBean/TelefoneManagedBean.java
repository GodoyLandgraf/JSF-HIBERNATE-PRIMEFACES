package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DAOTelefone;
import dao.DAOUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa user = new UsuarioPessoa();
	private DAOUsuario<UsuarioPessoa> daoUser = new DAOUsuario<UsuarioPessoa>();
	private DAOTelefone<TelefoneUser> daoTelefone = new DAOTelefone<TelefoneUser>();
	private TelefoneUser telefone = new TelefoneUser();
	
	
	@PostConstruct
	public void init() {
		
		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigouser");
		user = daoUser.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
	}
	
	public String salvar() {
		telefone.setUsuarioPessoa(user);
		daoTelefone.salvar(telefone);
		telefone = new TelefoneUser();
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		return "";
	}
	
	public void setDaoTelefone(DAOTelefone<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}
	
	public DAOTelefone<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}

	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}
	
	public UsuarioPessoa getUser() {
		return user;
	}
	
	public void setTelefone(TelefoneUser telefone) {
		this.telefone = telefone;
	}
	
	public TelefoneUser getTelefone() {
		return telefone;
	}
	
	public String removeTelefone() throws Exception {
		daoTelefone.deletarPorId(telefone);
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		telefone = new TelefoneUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone Removido!"));
		return "";
	}
	
}
