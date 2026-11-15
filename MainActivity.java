package com.vairos.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    private LinearLayout root, list;
    private EditText search;
    private final ArrayList<Tool> tools = new ArrayList<>();

    static class Tool {
        String name, category, description, install, command, note;
        Tool(String n,String c,String d,String i,String cmd,String nt) {
            name=n; category=c; description=d; install=i; command=cmd; note=nt;
        }
    }

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(8,13,27));
        getWindow().setNavigationBarColor(Color.rgb(8,13,27));
        seed();
        buildUi();
        render("");
    }

    private void seed() {
        tools.add(new Tool("Python","برمجة","لغة برمجة عامة لبناء السكريبتات والتطبيقات.","pkg install python -y","python --version","يمكنك تشغيل ملفاتك من مجلد home."));
        tools.add(new Tool("Nmap","الشبكات","اكتشاف الأجهزة والخدمات على شبكتك أو الأنظمة التي تملك تصريحًا لاختبارها.","pkg install nmap -y","nmap --version","استخدم الفحص فقط على شبكتك أو بإذن صريح."));
        tools.add(new Tool("Git","تطوير","إدارة إصدارات المشاريع وتنزيل المستودعات البرمجية.","pkg install git -y","git --version",""));
        tools.add(new Tool("Node.js","برمجة","تشغيل JavaScript وبناء أدوات وخوادم محلية.","pkg install nodejs -y","node --version",""));
        tools.add(new Tool("OpenSSH","الشبكات","اتصالات SSH آمنة وإدارة أجهزتك عن بُعد.","pkg install openssh -y","ssh -V","لا تشارك مفاتيحك أو كلمات المرور."));
        tools.add(new Tool("FFmpeg","وسائط","معالجة وتحويل ملفات الصوت والفيديو.","pkg install ffmpeg -y","ffmpeg -version",""));
        tools.add(new Tool("SQLite","قواعد البيانات","إنشاء وإدارة قواعد بيانات محلية خفيفة.","pkg install sqlite -y","sqlite3 --version",""));
        tools.add(new Tool("cURL","الشبكات","إرسال طلبات HTTP واختبار واجهات API.","pkg install curl -y","curl --version",""));
        tools.add(new Tool("Wget","الشبكات","تنزيل الملفات من الإنترنت عبر الطرفية.","pkg install wget -y","wget --version",""));
        tools.add(new Tool("Tmux","لينكس","إدارة عدة جلسات طرفية داخل نافذة واحدة.","pkg install tmux -y","tmux -V",""));
        tools.add(new Tool("Clang","تطوير","مترجم C/C++ لبناء البرامج من المصدر.","pkg install clang -y","clang --version",""));
        tools.add(new Tool("Nano","تطوير","محرر نصوص بسيط لكتابة وتعديل الملفات.","pkg install nano -y","nano --version",""));
    }

    private int dp(float v) { return (int)(v*getResources().getDisplayMetrics().density+0.5f); }
    private TextView text(String s,int size,int color) {
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); return t;
    }
    private void buildUi() {
        root=new LinearLayout(this); root.setOrientation(1);
        root.setPadding(dp(18),dp(20),dp(18),dp(12)); root.setBackgroundColor(Color.rgb(8,13,27));
        setContentView(root);

        TextView logo=text("VAIROS",30,Color.rgb(90,190,255)); logo.setTypeface(null,Typeface.BOLD);
        root.addView(logo);
        TextView sub=text("TOOLBOX  •  مركز أدوات Termux",14,Color.LTGRAY);
        root.addView(sub);
        TextView info=text("ابحث عن أداة، اقرأ شرحها، ثم ثبّتها أو شغّلها بضغطة واحدة.",13,Color.WHITE);
        info.setPadding(0,dp(14),0,dp(12)); root.addView(info);

        search=new EditText(this); search.setSingleLine(true); search.setTextColor(Color.WHITE);
        search.setHintTextColor(Color.GRAY); search.setHint("ابحث عن أداة أو تصنيف...");
        search.setPadding(dp(12),dp(8),dp(12),dp(8));
        search.setBackgroundColor(Color.rgb(24,34,54)); root.addView(search);
        search.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s,int st,int c,int a){}
            public void onTextChanged(CharSequence s,int st,int before,int count){render(s.toString());}
            public void afterTextChanged(android.text.Editable e){}
        });
        ScrollView scroll=new ScrollView(this);
        list=new LinearLayout(this); list.setOrientation(1); list.setPadding(0,dp(12),0,dp(20));
        scroll.addView(list); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        TextView foot=text("V1.0  •  يحتاج تثبيت Termux وتفعيل السماح بالتشغيل الخارجي",11,Color.GRAY);
        foot.setPadding(0,dp(8),0,0); root.addView(foot);
    }

    private void render(String q) {
        if(list==null) return;
        list.removeAllViews();
        String query=q.toLowerCase(Locale.ROOT).trim();
        for(Tool t:tools) {
            if(!(t.name.toLowerCase(Locale.ROOT).contains(query) ||
                 t.category.toLowerCase(Locale.ROOT).contains(query) ||
                 t.description.toLowerCase(Locale.ROOT).contains(query))) continue;
            LinearLayout card=new LinearLayout(this); card.setOrientation(1);
            card.setPadding(dp(14),dp(12),dp(14),dp(12));
            card.setBackgroundColor(Color.rgb(19,29,48));
            LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.bottomMargin=dp(10);
            list.addView(card,cp);

            TextView title=text(t.name+"   •   "+t.category,18,Color.rgb(100,195,255));
            title.setTypeface(null,Typeface.BOLD); card.addView(title);
            TextView desc=text(t.description,13,Color.WHITE); desc.setPadding(0,dp(7),0,dp(8)); card.addView(desc);

            LinearLayout buttons=new LinearLayout(this); buttons.setOrientation(0);
            Button install=new Button(this); install.setText("تثبيت");
            Button run=new Button(this); run.setText("تشغيل");
            buttons.addView(install,new LinearLayout.LayoutParams(0,-2,1));
            buttons.addView(run,new LinearLayout.LayoutParams(0,-2,1));
            card.addView(buttons);

            TextView details=text("أمر التشغيل: "+t.command+"\n"+t.note,11,Color.LTGRAY);
            details.setPadding(0,dp(6),0,0); card.addView(details);

            install.setOnClickListener(v->launchTermux("pkg install "+t.name.toLowerCase(Locale.ROOT)+" -y"));
            run.setOnClickListener(v->launchTermux(t.command));
        }
        if(list.getChildCount()==0) {
            TextView none=text("لا توجد نتائج مطابقة.",14,Color.WHITE);
            list.addView(none);
        }
    }

    private void launchTermux(String command) {
        try {
            Intent i=new Intent("com.termux.RUN_COMMAND");
            i.setClassName("com.termux","com.termux.app.RunCommandService");
            i.putExtra("com.termux.RUN_COMMAND_PATH","/data/data/com.termux/files/usr/bin/bash");
            i.putExtra("com.termux.RUN_COMMAND_ARGUMENTS",new String[]{"-lc",command+"; echo; echo '--- انتهى الأمر ---'; read -r -p 'اضغط Enter للعودة...' _"});
            i.putExtra("com.termux.RUN_COMMAND_WORKDIR","/data/data/com.termux/files/home");
            i.putExtra("com.termux.RUN_COMMAND_BACKGROUND",false);
            startService(i);
            Toast.makeText(this,"تم إرسال الأمر إلى Termux",Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            new AlertDialog.Builder(this).setTitle("تعذر تشغيل Termux")
                .setMessage("تأكد من تثبيت Termux من F-Droid أو GitHub، ثم فعّل Allow external apps في إعداداته.\n\nالتفاصيل: "+e.getMessage())
                .setPositiveButton("حسنًا",null).show();
        }
    }
}
