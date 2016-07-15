package cn.kyle.GRRemotePackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * Created by kyle on 16/7/15.
 */
public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        Element adsElement = new Element();
//        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)
                .setDescription("V 0.1\n本软件只是将 GR Remote 网页做了缓存\n添加了自动连接相机WIFI的功能\n本软件不保留任何权利.\n网站版权归于: RICOH IMAGING Co., Ltd.")
//                .addItem(getAppVersion())
//                .addItem(adsElement)
                .addGroup("联系我")
//                .addWebsite("https://github.com/BorntoGO/GR-Remote-Package")
//                .addFacebook("the.medy")
//                .addTwitter("medyo80")
//                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
//                .addPlayStore("com.ideashower.readitlater.pro")
//                .addInstagram("medyo80")
//                .addGitHub("BorntoGO")
//                .addItem(addCustomWebsite("baidu.com", "我的博客"))
//                .addItem(addSteam("http://steamcommunity.com/profiles/76561198040853930", "来啊,互相伤害啊!"))
                .addItem(addCustomWebsite("https://github.com/BorntoGO/GR-Remote-Package", "项目地址", mehdi.sakout.aboutpage.R.drawable.about_icon_github, true))
                .addItem(addCustomWebsite("http://borntogo.github.io/", "部落格", 0, false))
                .addItem(addCustomWebsite("http://steamcommunity.com/profiles/76561198040853930", "来啊,互相伤害啊!", R.drawable.steam, true))
//                .addItem(getCopyRightsElement())

                .addEmail("justregisterid@gmail.com")
                .create();

        setContentView(aboutPage);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
//        final String copyrights = String.format( Calendar.getInstance().get(Calendar.YEAR) + "");
        final String copyrights = "本软件不保留任何权利.\n网站版权归于: RICOH IMAGING Co., Ltd.";

        copyRightsElement.setTitle(copyrights);
//        copyRightsElement.setIcon(R.drawable.steam);

//        copyRightsElement.setIcon(R.drawable.about_icon_copy_right);
        copyRightsElement.setColor(ContextCompat.getColor(this, mehdi.sakout.aboutpage.R.color.about_item_icon_color));
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(About.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }






    Element addCustomWebsite(String url, String title, int cusicon, Boolean showcusicon) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        int icon = mehdi.sakout.aboutpage.R.drawable.about_icon_link;
        if (showcusicon){
            icon = cusicon;
        }

        Element websiteElement = new Element();
        websiteElement.setTitle(title);
//        websiteElement.setIcon(mehdi.sakout.aboutpage.R.drawable.about_icon_link);
        websiteElement.setIcon(icon);
        websiteElement.setColor(ContextCompat.getColor(this, mehdi.sakout.aboutpage.R.color.about_item_icon_color));
        websiteElement.setValue(url);

        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

        websiteElement.setIntent(browserIntent);

        return websiteElement;
    }


    Element getAppVersion() {
        Element copyRightsElement = new Element();
//        final String copyrights = String.format( Calendar.getInstance().get(Calendar.YEAR) + "");
        final String copyrights = "Version 6.2";

        copyRightsElement.setTitle(copyrights);
//        copyRightsElement.setIcon(R.drawable.steam);

//        copyRightsElement.setIcon(R.drawable.about_icon_copy_right);
//        copyRightsElement.setColor(ContextCompat.getColor(this, mehdi.sakout.aboutpage.R.color.about_item_icon_color));
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(About.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

}
