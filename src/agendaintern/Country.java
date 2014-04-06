/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaintern;

/**
 *
 * @author Claudiu
 */
public class Country {

    private String limba;
    private String limba_scurt;
    public String name;
    private String name_scurt;

    Country(String name, String name_scurt, String limba, String limba_scurt) {
        this.limba = limba;
        this.limba_scurt = limba_scurt;
        this.name = name;
        this.name_scurt = name_scurt;
    }

    public String getLimba() {
        return limba;
    }

    public void setLimba(String limba) {
        this.limba = limba;
    }

    public String getLimba_scurt() {
        return limba_scurt;
    }

    public void setLimba_scurt(String limba_scurt) {
        this.limba_scurt = limba_scurt;
    }

    public String getName_scurt() {
        return name_scurt;
    }

    public void setName_scurt(String name_scurt) {
        this.name_scurt = name_scurt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
