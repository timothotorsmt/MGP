package com.sdm.mgpica;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.app.DialogFragment;

public class NameInputDialogFragment extends DialogFragment{
    private String m_Text = "";
    public static boolean IsShown = false;
    public static boolean entered = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;
        entered = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Save your score");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View textEnter = inflater.inflate(R.layout.nameinput, null);
        builder.setView(textEnter);

        final EditText nameInput = textEnter.findViewById(R.id.input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = nameInput.getText().toString();
                GameSystem.Instance.SaveEditBegin();
                GameSystem.Instance.SetStringInSave("Name", m_Text);
                GameSystem.Instance.SaveEditEnd();
                entered = true;
                IsShown = false;

                LeaderboardSort();

                GamePage.Instance.toLossScreen();
            }
        });
        return builder.create();
    }

    private void LeaderboardSort()
    {
        GameSystem.Instance.SaveEditBegin();

        if (GameSystem.Instance.GetIntFromSave("Score") > GameSystem.Instance.GetIntFromSave("Num3Score")) {
            GameSystem.Instance.SetIntInSave("Num3Score", GameSystem.Instance.GetIntFromSave("Score"));
            GameSystem.Instance.SetStringInSave("Num3Str", GameSystem.Instance.GetStringFromSave("Name") + " [" + GameSystem.Instance.GetIntFromSave("Score") + "]");
        }

        if (GameSystem.Instance.GetIntFromSave("Score") > GameSystem.Instance.GetIntFromSave("Num2Score")) {
            GameSystem.Instance.SetIntInSave("Num3Score", GameSystem.Instance.GetIntFromSave("Num2Score"));
            GameSystem.Instance.SetStringInSave("Num3Str", GameSystem.Instance.GetStringFromSave("Num2Str"));

            GameSystem.Instance.SetIntInSave("Num2Score", GameSystem.Instance.GetIntFromSave("Score"));
            GameSystem.Instance.SetStringInSave("Num2Str", GameSystem.Instance.GetStringFromSave("Name") + " [" + GameSystem.Instance.GetIntFromSave("Score") + "]");
        }

        if (GameSystem.Instance.GetIntFromSave("Score") > GameSystem.Instance.GetIntFromSave("Num1Score")){
            GameSystem.Instance.SetIntInSave("Num2Score", GameSystem.Instance.GetIntFromSave("Num1Score"));
            GameSystem.Instance.SetStringInSave("Num2Str", GameSystem.Instance.GetStringFromSave("Num1Str"));

            GameSystem.Instance.SetIntInSave("Num1Score", GameSystem.Instance.GetIntFromSave("Score"));
            GameSystem.Instance.SetStringInSave("Num1Str", GameSystem.Instance.GetStringFromSave("Name") + " [" + GameSystem.Instance.GetIntFromSave("Score") + "]");
        }

        GameSystem.Instance.SaveEditEnd();
    }
}