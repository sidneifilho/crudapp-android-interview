package com.sidnei.crudapp.view.saveOrUpdatePerson;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.sidnei.crudapp.R;
import com.sidnei.crudapp.model.Person;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonSaveOrUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonSaveOrUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonSaveOrUpdateFragment extends Fragment implements IPersonSaveOrUpdateView {

    /// fields
    private EditText etName;
    private EditText etCPF;
    private EditText etCEP;
    private RadioGroup rgSex;
    private RadioButton rbSexFemale;
    private RadioButton rbSexMale;
    private RadioButton rbSexOther;
    private Spinner spnUF;
    private EditText etAddress;
    private Button btnSave;
    private Button btnCancel;

    private OnFragmentInteractionListener mListener;

    private ArrayAdapter<String> ufAdapter;

    private PersonSaveOrUpdatePresenter personPresenter;

    public PersonSaveOrUpdateFragment() {

    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     * This is a way to communicate between the Parent Activity before start
     * @param p Person object with the values that we want edit
     * @return A new instance of fragment PersonSaveOrUpdateFragment.
     */
    public static PersonSaveOrUpdateFragment newInstance(Person p) {
        PersonSaveOrUpdateFragment fragment = new PersonSaveOrUpdateFragment();

        try{
            /// getting the values from arguments
            Bundle args = new Bundle();
            args.putSerializable("person", p);
            fragment.setArguments(args);
        }catch (Exception ex){
            Log.d("PersonSaveOrUpdateFragment", "Erro when create a new instance, ERROR: " + ex.getMessage());
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment so that it will not be destroyed when an orientation
        // change happens and we can keep our AsyncTask running
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_save_or_update, container, false);

        /// fields get
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etCEP = view.findViewById(R.id.etCEP);
        etCPF = view.findViewById(R.id.etCPF);
        rgSex = view.findViewById(R.id.rgSex);
        rbSexFemale = view.findViewById(R.id.rbSexFemale);
        rbSexMale = view.findViewById(R.id.rbSexMale);
        rbSexOther = view.findViewById(R.id.rbSexOther);
        spnUF = view.findViewById(R.id.spnUF);

        // attaching data adapter to spinner
        String states[] = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        ufAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, states);
        ufAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnUF.setAdapter(ufAdapter);

        /// init the presenter with the activity associate to the fragment
        personPresenter = new PersonSaveOrUpdatePresenter(this, view.getContext());

        /// if exist arguments than we will update the argPerson because we will edit the Person Object
        if (getArguments() != null) {
            personPresenter.setPerson((Person) getArguments().getSerializable("person"));
        }else{
            personPresenter.setPerson(new Person());
        }

        /// configuring the event of the fields to update the values in the presenter object
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                personPresenter.setName(editable.toString());
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                personPresenter.setAddress(editable.toString());
            }
        });

        etCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                personPresenter.setCEP(editable.toString());
            }
        });

        etCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                personPresenter.setCPF(editable.toString());
            }
        });

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rgSex.findViewById(checkedId);
                int index = rgSex.indexOfChild(radioButton);
                personPresenter.setSex(index);
            }
        });

        spnUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                personPresenter.setUF(spnUF.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ///When we clicked in the cancel button we will finish the current Activy, and we will be back to the main activity
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personPresenter.save();
            }
        });

        ///When we clicked in the cancel button we will finish the current Activy, and we will be back to the main activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personPresenter.cancel();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()  + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        /// destroying the presenter
        if(personPresenter != null) {
            personPresenter.onDestroy();
        }
        personPresenter = null;
        mListener = null;

        super.onDetach();
    }

    /***/
    @Override
    public void showProgress(){
        /// @todo implements
    }

    /***/
    @Override
    public void hideProgress(){
        /// @todo implements
    }

    /***/
    @Override
    public void updateFields(Person p){
        if (p != null) {
            etName.setText(p.getName());
            etCPF.setText(p.getCpf());
            etCEP.setText(p.getCep());
            etAddress.setText(p.getAddress());
            spnUF.setSelection(ufAdapter.getPosition(p.getUf()));

            switch (p.getSex()) {
                case FEMALE:
                    rbSexFemale.setChecked(true);
                    break;
                case MALE:
                    rbSexMale.setChecked(true);
                    break;
                case OTHER:
                    rbSexOther.setChecked(true);
                    break;
            }
        }
    }

    /**
     * Function used to clear the fields values and the argPerson used to save or update the record in the value
     * */
    @Override
    public void clearFields(){
        etName.setText("");
        etCPF.setText("");
        etCEP.setText("");
        etAddress.setText("");
        spnUF.setSelection(0);
        rbSexOther.setChecked(true);

        etName.requestFocus();
    }

    /***/
    @Override
    public void showSaveSuccessful(){
        try{
            Toast.makeText(this.getActivity().getApplicationContext(), "Cadastro salvo com sucesso!", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.d("showSave", "ERROR: " + ex.getMessage());
        }
    }

    /***/
    @Override
    public void showSaveFail(){
        try{
            Toast.makeText(this.getActivity().getApplicationContext(), "Erro ao tentar cadastrar pessoa.", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.d("showSave", "ERROR: " + ex.getMessage());
        }
    }

    /***/
    @Override
    public void setNameError(){
        etName.setError("Nome é inválido!");
    }

    /***/
    @Override
    public void setCpfError(){
        etCPF.setError("CPF é inválido!");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String str);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.onFragmentInteraction(str);
        }
    }
}