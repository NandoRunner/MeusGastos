package fandradetecinfo.com.meusgastos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import fandradetecinfo.com.meusgastos.Views.Fragment01;
import fandradetecinfo.com.meusgastos.Views.Fragment02;
import fandradetecinfo.com.meusgastos.Views.Fragment03;
import fandradetecinfo.com.meusgastos.Views.Fragment04;

public class MainActivity extends AppCompatActivity {

    public static String veiculo = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return Fragment01.newInstance("Cadastro, Instance 1");
                case 1:
                    return Fragment02.newInstance("Abastecimento, Instance 1");
                case 2:
                    return Fragment03.newInstance("Consumo, Instance 1");
                case 3:
                    return Fragment04.newInstance("Totais, Instance 1");
                default:
                    return Fragment02.newInstance("Abastecimento, Instance 1");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}


