package managedBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.hibernate.exception.ConstraintViolationException;

import com.google.gson.Gson;

import antlr.debug.NewLineEvent;
import dao.DAOUsuario;
import dao.DaoGeneric;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DAOUsuario<UsuarioPessoa> daoGeneric = new DAOUsuario<UsuarioPessoa>();
	
	@PostConstruct
	public void init() {
		list = daoGeneric.listar(UsuarioPessoa.class);
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	
	public String salvar() {
		daoGeneric.salvar(usuarioPessoa);
		list.add(usuarioPessoa);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		return "";
	}
	
	public String novo() {
	usuarioPessoa = new UsuarioPessoa();
	return "";
	}
	
	public List<UsuarioPessoa> getList() {
		return list;
	}
	
	public String remover() {
		try {
			daoGeneric.removerUsuario(usuarioPessoa);
			list.remove(usuarioPessoa);
			usuarioPessoa = new UsuarioPessoa();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
			return "";
		} catch (Exception e) {
			if(e.getCause() instanceof ConstraintViolationException) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informação: ", "Existem telefones para o usuário"));
			}else {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event){
		try {
			System.out.println("Cep digitado: " + usuarioPessoa.getCep());
			URL url = new URL("https://viacep.com.br/ws/"+usuarioPessoa.getCep()+"/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			while((cep = br.readLine())!= null) {
				jsonCep.append(cep);
			}
			UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
			usuarioPessoa.setCep(userCepPessoa.getCep());
			usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
			usuarioPessoa.setComplemento(userCepPessoa.getComplemento());
			usuarioPessoa.setBairro(userCepPessoa.getBairro());
			usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
			usuarioPessoa.setUf(userCepPessoa.getUf());
			usuarioPessoa.setIbge(userCepPessoa.getIbge());
			usuarioPessoa.setGia(userCepPessoa.getGia());
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
	}

	}

