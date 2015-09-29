package luh.energiesparen;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;


public class ImpressumActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView impressum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressum);

        // Enable Toolbar and their Navigation Ability
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        impressum = (TextView) findViewById(R.id.textView_impressum01);
        impressum.setText(Html.fromHtml("<h1>Impressum</h1>" +
                "<h2>Angaben gemäß § 5 TMG:</h2>" +
                "<p>Max Mustermann<br />" +
                "Mega Musterladen<br />" +
                "Musterstraße 111<br />" +
                "Gebäude 44<br />" +
                "90210 Musterstadt" +
                "</p>" +
                "<h2>Kontakt:</h2>" +
                "Telefon: +49 (0) 123 44 55 66<br/>" +
                "Telefax:+49 (0) 123 44 55 99<br/>" +
                "E-Mail: mustermann@musterfirma.de<br/></p>" +
                "<h2>Umsatzsteuer-ID:</h2>" +
                "<p>Umsatzsteuer-Identifikationsnummer gemäß §27 a Umsatzsteuergesetz:<br />" +
                "DE 999 999 999</p>" +
                "<h2>Quellenangaben für die verwendeten Bilder und Grafiken:</h2>" +
                "<div>Grafiken erstellt durch <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> von <a href=\"http://www.flaticon.\" title=\"Flaticon\">www.flaticon.com</a> sind lizensiert unter <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\">CC BY 3.0</a></div>" +
                "<p> </p>" +
                "<p>Quelle: <em><a rel=\"nofollow\" href=\"http://www.e-recht24.de/impressum-generator.html\">http://www.e-recht24.de</a></em></p>\n"));
    }
}
