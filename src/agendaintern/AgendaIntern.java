/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaintern;

/**
 *
 * @author Claudiu
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.swing.JLabel;

/**
 *
 * @author alex
 */
public class AgendaIntern extends javax.swing.JApplet {
    
    public static final String DATE_FORMAT_NOW = "dd-MM-yyyy";
    Locale language = null;
    List<Country> countries = null;
    int index = 0;
    Calendar calendar = null;
    Clock clock = null;
    TimeZone t;
    Map<String, HashSet<TimeZone>> availableTimezones = new HashMap<>();
    
    public JLabel getjLabel2() {
        return jLabel2;
    }

    /**
     * Initializes the applet
     */
    @Override
    public void init() {

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    initComponents();
                    jList1.setBackground(Color.white);
                    jList2.setBackground(Color.white);
                    addCountries();
                    jComboBox1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doSomething();
                        }
                    });
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            ex.getMessage();
        }
    }
    
    public void addCountries() {
        
        countries = new ArrayList<>();
        
        Locale[] s = Locale.getAvailableLocales();
        
        for (int i = 0; i < s.length; i++) {
            
            String limba_scurt = s[i].getLanguage();
            String tara_scurt = s[i].getCountry();
            String limba_nume = s[i].getDisplayName();
            String tara_nume = s[i].getDisplayCountry();
            
            if ((limba_nume.length() > 1) && (limba_scurt.length() > 1) && (tara_nume.length() > 1) && (tara_scurt.length() > 1)) {
                countries.add(new Country(tara_nume, tara_scurt, limba_nume, limba_scurt));
            }
        }
        
        Collections.sort(countries, new CountryComparator());
        for (int j = 0; j < countries.size() - 1; j++) {
            if (countries.get(j).getName().equals(countries.get(j + 1).getName())) {
                countries.remove(j);
            }
        }
        
        jComboBox1.removeAllItems();
        for (int j = 0; j < countries.size() - 1; j++) {
            jComboBox1.addItem(countries.get(j).getName());
        }
        
        clock = new Clock(this);
    }
    
    public void showClock() {
        clock.start();
    }
    
    public void showDate() {
        
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NOW);
        format.setTimeZone(TimeZone.getDefault());
        jLabel3.setText("Current Date: " + format.format(calendar.getTime()));
    }
    
    public void showDays() {
        final List<String> zile = new ArrayList<String>();
        jList1.removeAll();
        
        for (int i = 1; i < 8; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, i);
            zile.add(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, language));
        }
        
        jList1.setModel(new javax.swing.AbstractListModel() {
            List<String> strings = zile;
            
            @Override
            public int getSize() {
                return zile.size();
            }
            
            @Override
            public Object getElementAt(int i) {
                return strings.get(i);
            }
        });
    }
    
    public void showMonths() {
        final List<String> luni = new ArrayList<String>();
        jList2.removeAll();
        
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            luni.add(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, language));
        }
        
        jList2.setModel(new javax.swing.AbstractListModel() {
            List<String> strings = luni;
            
            @Override
            public int getSize() {
                return luni.size();
            }
            
            @Override
            public Object getElementAt(int i) {
                return luni.get(i);
            }
        });
        
    }
    
    public void getOtherDetails() {
        jLabel6.setText("TimeZone: " + TimeZone.getDefault().getDisplayName());
        jLabel8.setText("Language: " + language.getDisplayLanguage());
        jLabel7.setText("Currency: " + Currency.getInstance(Locale.getDefault()).getCurrencyCode());
    }
    
    public void getAvailableTimeZones() {
        
        final String countryCode = countries.get(index).getName_scurt();
        
        HashSet<TimeZone> timezones = availableTimezones.get(countryCode);
        if (timezones == null) {
            timezones = new HashSet<>();
            // Find all timezones for that country (code) using ICU4J

            final List<String> zones = new ArrayList<>();
            
            for (String id : com.ibm.icu.util.TimeZone.getAvailableIDs(countryCode)) {
                // Add timezone to result map
                zones.add(id);
                timezones.add(TimeZone.getTimeZone(id));
            }
            availableTimezones.put(countryCode, timezones);
            if (zones.size() > 0) {
                TimeZone timezone = TimeZone.getTimeZone((String) zones.get(0));
                TimeZone.setDefault(timezone);
            }
        } else {
            TimeZone.setDefault(timezones.iterator().next());
        }
        
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        
        setMaximumSize(new java.awt.Dimension(776, 354));
        setMinimumSize(new java.awt.Dimension(776, 354));
        
        jLabel1.setText("Select country");
        
        jLabel4.setText("Days of the week");
        
        jLabel5.setText("Months of the year");
        
        
        jScrollPane1.setViewportView(jList1);
        
        jScrollPane2.setViewportView(jList2);
        
        jLabel6.setText("Time Zone");
        
        jLabel7.setText("Currency");
        
        jLabel8.setText("Language");
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel5)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel7)
                .addComponent(jLabel6)
                .addComponent(jLabel8))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8))
                .addComponent(jScrollPane1)
                .addComponent(jScrollPane2))
                .addContainerGap(82, Short.MAX_VALUE)));
        
        jLabel2.setText("Clock");
        
        jLabel3.setText("Date");
        
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jLabel3))
                .addContainerGap(19, Short.MAX_VALUE)));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }
    
    private void doSomething() {
        index = jComboBox1.getSelectedIndex();
        language = new Locale(countries.get(index).getLimba_scurt(), countries.get(index).getName_scurt());
        Locale.setDefault(language);
        calendar = Calendar.getInstance(language);
        
        getAvailableTimeZones();
        t = TimeZone.getDefault();
        showDate();
        showDays();
        showMonths();
        getOtherDetails();
        showClock();
    }
    // Variables declaration - do not modify                     
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration                   
}
