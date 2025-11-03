package com.example.bt_formconstrainlayout

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_constrain_layout)

        val editFirstName: EditText = findViewById(R.id.FirstName)
        val editLastName: EditText = findViewById(R.id.LastName)
        val radioGroupGender: RadioGroup = findViewById(R.id.radioGroupGender)
        val editBirthday: EditText = findViewById(R.id.editTxtBirthday)
        val calendarView: CalendarView = findViewById(R.id.calendarViewBirthday)
        val btnSelect: Button = findViewById(R.id.button)
        val editAddress: EditText = findViewById(R.id.editTxtAddress)
        val editEmail: EditText = findViewById(R.id.editTxtEmail)
        val checkTerms: CheckBox = findViewById(R.id.checkBox)
        val btnRegister: Button = findViewById(R.id.Register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        btnSelect.setOnClickListener {
            calendarView.visibility =
                if (calendarView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateStr = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
            editBirthday.setText(dateStr)
            calendarView.visibility = View.GONE
        }

        btnRegister.setOnClickListener {
            validateForm(
                editFirstName,
                editLastName,
                radioGroupGender,
                editBirthday,
                editAddress,
                editEmail,
                checkTerms
            )
        }
    }

    /** Kiểm tra hợp lệ dữ liệu form **/
    private fun validateForm(
        firstName: EditText,
        lastName: EditText,
        genderGroup: RadioGroup,
        birthday: EditText,
        address: EditText,
        email: EditText,
        terms: CheckBox
    ) {
        val defaultColor = Color.parseColor("#CCC7C7")

        val inputs = listOf(firstName, lastName, birthday, address, email)
        inputs.forEach { it.setBackgroundColor(defaultColor) }
        genderGroup.setBackgroundColor(Color.TRANSPARENT)
        terms.setBackgroundColor(Color.TRANSPARENT)

        var isValid = true

        fun highlightIfEmpty(edit: EditText): Boolean {
            return if (edit.text.toString().trim().isEmpty()) {
                edit.setBackgroundColor(Color.RED)
                false
            } else true
        }

        isValid = highlightIfEmpty(firstName) && isValid
        isValid = highlightIfEmpty(lastName) && isValid
        isValid = highlightIfEmpty(birthday) && isValid
        isValid = highlightIfEmpty(address) && isValid

        val emailText = email.text.toString().trim()
        if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (genderGroup.checkedRadioButtonId == -1) {
            genderGroup.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (!terms.isChecked) {
            terms.setBackgroundColor(Color.RED)
            isValid = false
        }

        Toast.makeText(
            this,
            if (isValid) "Registered successfully" else "Please fill all required fields",
            Toast.LENGTH_SHORT
        ).show()
    }
}
