/**
 * Created by khadad on 01/06/2017.
 */
public class ClientUser {

    public boolean inadiplencia;
    public String nome;
    public String sobrenome;
    public String cpf;
    public int saldo;
    public int limiteCC;
    public IPVA ipva;
    public String telefone;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public IPVA getIpva() {
        return ipva;
    }

    public void setIpva(IPVA ipva) {
        this.ipva = ipva;
    }

    public int getLimiteCC() {
        return limiteCC;
    }

    public void setLimiteCC(int limiteCC) {
        this.limiteCC = limiteCC;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean getInadiplencia() {
        return inadiplencia;
    }

    public void setInadiplencia(boolean inadiplencia) {
        this.inadiplencia = inadiplencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }



}
