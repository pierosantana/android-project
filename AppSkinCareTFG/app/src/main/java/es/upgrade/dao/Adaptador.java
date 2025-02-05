package es.upgrade.dao;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import es.upgrade.UI.OptionFragment;
import es.upgrade.entidad.SkinType;

public class Adaptador extends FragmentStateAdapter {
    private List<SkinType> tiposDePiel;

    public Adaptador(@NonNull FragmentActivity fragmentActivity, List<SkinType> tiposDePiel) {
        super(fragmentActivity);
        this.tiposDePiel = tiposDePiel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OptionFragment.newInstance(tiposDePiel.get(position));
    }

    @Override
    public int getItemCount() {
        return tiposDePiel.size();
    }
}
