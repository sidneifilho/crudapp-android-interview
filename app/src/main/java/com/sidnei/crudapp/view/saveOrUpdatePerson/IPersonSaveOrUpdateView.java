package com.sidnei.crudapp.view.saveOrUpdatePerson;

import com.sidnei.crudapp.model.Person;

public interface IPersonSaveOrUpdateView {

    void showProgress();

    void hideProgress();

    void clearFields();

    void showSaveSuccessful();

    void showSaveFail();

    void setNameError();

    void setCpfError();

    void updateFields(Person p);

}