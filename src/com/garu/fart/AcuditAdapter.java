package com.garu.fart;

import java.util.TreeSet;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AcuditAdapter extends BaseAdapter {

	class Vista {
		public TextView titol;
	}

	private Cursor dades;
	private Activity context;
	// tots els índexs checkeds van aquí
    private TreeSet<Integer> checkedItems;
	// indicador de modalitat selecció múltiple
    private boolean multiMode;

	public AcuditAdapter(Activity context, Cursor dades) {
		super();
		this.context = context;
		this.dades = dades;
		this.checkedItems = new TreeSet<Integer>();
	}
	
	public void enterMultiMode()
    {
        this.multiMode = true;
        this.notifyDataSetChanged();
    }

    public void exitMultiMode()
    {
        this.checkedItems.clear();
        this.multiMode = false;
        this.notifyDataSetChanged();
    }
    
    public void setChecked(int pos, boolean checked)
    {
        if (checked) {
            this.checkedItems.add(Integer.valueOf(pos));
        } else {
            this.checkedItems.remove(Integer.valueOf(pos));
        }
        if (this.multiMode) {
            this.notifyDataSetChanged();
        }
    }

    public boolean isChecked(int pos)
    {
        return this.checkedItems.contains(Integer.valueOf(pos));
    }

    public void toggleChecked(int pos)
    {
        final Integer v = Integer.valueOf(pos);
        if (this.checkedItems.contains(v)) {
            this.checkedItems.remove(v);
        } else {
            this.checkedItems.add(v);
        }
        this.notifyDataSetChanged();
    }

    public int getCheckedItemCount()
    {
        return this.checkedItems.size();
    }

    // we use this convinience method for rename thingie.
    public Acudit getFirstCheckedItem()
    {
        for (Integer i : this.checkedItems) {
            return getItem(i.intValue());
        }
        return null;
    }

    public TreeSet<Integer> getCheckedItems()
    {
        return this.checkedItems;
    }

    public void close()
    {
        this.dades.close();
    }
    
    public void updateData()
    {
        this.checkedItems.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return this.dades.getCount();
    }


	public View getView(int position, View convertView, ViewGroup parent) {
		View element = convertView;
		Vista vista;
		
		// optimització de codi per vizualitzar mes ràpid
		if (element == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			element = inflater.inflate(R.layout.llistat, null);

			vista = new Vista();
			vista.titol = (TextView) element.findViewById(R.id.textTitol);

			element.setTag(vista);
		} else {
			vista = (Vista) element.getTag();
		}

		vista.titol.setText(getItem(position).getTitol());

		element.setBackgroundResource(this.multiMode ? R.drawable.selector_list_multimode : R.drawable.selector_list);
		
		if (checkedItems.contains(Integer.valueOf(position))) {
            // if this item is checked - set checked state
            element.getBackground().setState(new int[] { android.R.attr.state_checked });
        } else {
            // if this item is unchecked - set unchecked state (notice the minus)
            element.getBackground().setState(new int[] { -android.R.attr.state_checked });
        }
		
		return element;
	}

	@Override
	public Acudit getItem(int position) {
		Acudit acu = new Acudit();
		if(dades.moveToPosition(position)){
			acu.setNum(dades.getInt(0));
			acu.setTitol(dades.getString(1));
			acu.setCos(dades.getString(2));
			acu.setRate(dades.getFloat(3));
		}
		return acu;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getNum();
	}

	public void setDades(Cursor dades) {
		this.dades = dades;
	}
}
