/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controlPanel.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "GE_FORNECEDOR_SERV_TERCEIROS")
@NamedQueries({
    @NamedQuery(name = "GeFornecedorServTerceiros.findAll", query = "SELECT g FROM GeFornecedorServTerceiros g")})
public class GeFornecedorServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "TELEFONE")
    private String telefone;
    @Basic(optional = false)
    @Column(name = "ENDERECO")
    private String endereco;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFornServTerceiros")
    private Collection<GeFornTipoServTerceiros> geFornTipoServTerceirosCollection;
    @OneToMany(mappedBy = "idFornServTerceiros")
    private Collection<GeSegmentoServTerceiros> geSegmentoServTerceirosCollection;

    public GeFornecedorServTerceiros() {
    }

    public GeFornecedorServTerceiros(Long id) {
        this.id = id;
    }

    public GeFornecedorServTerceiros(Long id, String descricao, String telefone, String endereco, String email) {
        this.id = id;
        this.descricao = descricao;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<GeFornTipoServTerceiros> getGeFornTipoServTerceirosCollection() {
        return geFornTipoServTerceirosCollection;
    }

    public void setGeFornTipoServTerceirosCollection(Collection<GeFornTipoServTerceiros> geFornTipoServTerceirosCollection) {
        this.geFornTipoServTerceirosCollection = geFornTipoServTerceirosCollection;
    }

    public Collection<GeSegmentoServTerceiros> getGeSegmentoServTerceirosCollection() {
        return geSegmentoServTerceirosCollection;
    }

    public void setGeSegmentoServTerceirosCollection(Collection<GeSegmentoServTerceiros> geSegmentoServTerceirosCollection) {
        this.geSegmentoServTerceirosCollection = geSegmentoServTerceirosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeFornecedorServTerceiros)) {
            return false;
        }
        GeFornecedorServTerceiros other = (GeFornecedorServTerceiros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaoequipamentos.entity.GeFornecedorServTerceiros[ id=" + id + " ]";
    }
    
}
