package com.gureev.omdbapp.fragments.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gureev.omdbapp.R
import com.gureev.omdbapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initContactButtons()
        return binding.root
    }

    private fun initContactButtons() {
        binding.buttonSendEmail.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_EMAIL, getString(R.string.email_address))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.buttonOpenGithubLink.setOnClickListener {
            openWebPage(getString(R.string.github_url))
        }

        binding.buttonOpenLiLink.setOnClickListener {
            openWebPage(getString(R.string.linkedin_url))
        }

        binding.buttonOpenHhLink.setOnClickListener {
            openWebPage(getString(R.string.hh_url))
        }

        binding.buttonTelegram.setOnClickListener {
            openWebPage(getString(R.string.telegram_url))

        }

        binding.buttonVk.setOnClickListener {
            openWebPage(getString(R.string.vk_url))
        }

        binding.buttonYoutube.setOnClickListener {
            openWebPage(getString(R.string.youtube_url))
        }
    }

    private fun openWebPage(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, "There is no suitable application.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}