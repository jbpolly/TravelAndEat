package com.mysticraccoon.travelandeat.ui.credits

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.mysticraccoon.travelandeat.databinding.FragmentCreditsBinding

class CreditsFragment: Fragment() {

    private lateinit var binding: FragmentCreditsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCredits()
    }

    private fun setupCredits() {

        val freepikHtml="Icons made by <a href=\"https://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
        val dDaraHtml = "Icons made by <a href=\"https://www.flaticon.com/authors/ddara\" title=\"dDara\">dDara</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
        val bimbimkha = "<a href='https://www.freepik.com/bimbimkha'>Food vector created by bimbimkha - www.freepik.com</a>"

        val freepikSpanned = HtmlCompat.fromHtml(freepikHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.iconFreepik.text = freepikSpanned
        binding.iconFreepik.movementMethod = LinkMovementMethod.getInstance()

        val dDaraSpanned = HtmlCompat.fromHtml(dDaraHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.iconDDara.text = dDaraSpanned
        binding.iconDDara.movementMethod = LinkMovementMethod.getInstance()

        val bimbimkhaSpanned = HtmlCompat.fromHtml(bimbimkha, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.iconBimbimkha.text = bimbimkhaSpanned
        binding.iconBimbimkha.movementMethod = LinkMovementMethod.getInstance()

    }


}