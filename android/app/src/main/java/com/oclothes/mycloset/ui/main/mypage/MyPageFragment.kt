package com.oclothes.mycloset.ui.main.mypage

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oclothes.mycloset.databinding.FragmentMyPageBinding
import com.oclothes.mycloset.ui.BaseFragment
import com.oclothes.mycloset.ui.main.mypage.modify.ModifyInfoActivity
import com.oclothes.mycloset.utils.RegulationUtils
import com.oclothes.mycloset.utils.getUser


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun initAfterBinding() {
        init()
        setOnClickListeners()
    }

    private fun init() {
        getUser()?.let{
            binding.myPageEmailTv.text = it.email
        }
    }

    fun setEmail(){
        getUser()?.let{
            binding.myPageEmailTv.text = it.email
        }
    }

    private fun setOnClickListeners() {
        binding.myPageSetAccountInfoBtn.setOnClickListener {
            val intent = Intent(requireContext(), ModifyInfoActivity::class.java)
            intent.putExtra("mode", 1)
            startActivity(intent)
        }

        binding.myPageSetPersonalInfoBtn.setOnClickListener {
            val intent = Intent(requireContext(), ModifyInfoActivity::class.java)
            intent.putExtra("mode", 2)
            startActivity(intent)
        }

        binding.myPageAskBtn.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("문의하기")
                .setMessage("성명 :박동규\n" +
                        "연락처 :01038879173, rnlgksclsrn9868@gmail.com")
                .create()
                .show()
        }

        binding.myPagePersonalInfoRegulationBtn.setOnClickListener {
            RegulationUtils.showPersonalInfoRegulation(requireContext())
        }

        binding.myPageServiceRegulationBtn.setOnClickListener {
            RegulationUtils.showUsageRegulation(requireContext())
        }
    }

    fun backPressed() : Boolean {
        return true
    }
}