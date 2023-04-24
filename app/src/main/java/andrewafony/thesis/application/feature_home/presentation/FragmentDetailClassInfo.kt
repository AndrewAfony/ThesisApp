package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoBinding
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoTabDeadlinesBinding
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoTabInfoBinding
import andrewafony.thesis.application.feature_home.presentation.professor_info.BottomSheetFragmentProfessorInfo
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class FragmentDetailClassInfo : BaseFragment<FragmentDetailClassInfoBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    private lateinit var viewPager: ViewPager2

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoBinding
        get() = FragmentDetailClassInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DetailClassInfoTabAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            if (position == 0) tab.text = "Info"
            else tab.text = "Deadlines"
        }.attach()

        binding.toolbar.setNavigationOnClickListener {
            mainViewModel.navigateBack()
        }

        viewModel.observeClassInfo(this) {
            binding.toolbar.title = "${it.order} class"
        }
    }
}

class DetailClassInfoTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FragmentDetailClassInfoFirstTab()
        else FragmentDetailClassInfoTabDeadlines()
    }
}

class FragmentDetailClassInfoFirstTab : BaseFragment<FragmentDetailClassInfoTabInfoBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoTabInfoBinding
        get() = FragmentDetailClassInfoTabInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeProfessorInfoLoadingState(this) { loading ->
            binding.run {
                professorCard.isEnabled = !loading

                if (loading) {
                    shimmerHolder.visibility = View.VISIBLE
                    shimmerHolder.startShimmer()
                    professorPhoto.visibility = View.INVISIBLE
                    professorName.visibility = View.INVISIBLE
                    professorPosition.visibility = View.INVISIBLE
                }
                else {
                    shimmerHolder.visibility = View.GONE
                    shimmerHolder.stopShimmer()
                    professorPhoto.visibility = View.VISIBLE
                    professorName.visibility = View.VISIBLE
                    professorPosition.visibility = View.VISIBLE
                }
            }
        }
        viewModel.observeProfessorInfo(this) {
            binding.run {
                professorName.text = it.name
                professorPosition.text = it.position
                Glide.with(root)
                    .load(it.photo)
                    .into(binding.professorPhoto)
            }

        }
        viewModel.observeClassInfo(this) { classInfo ->
            viewModel.loadProfessorInfo(classInfo.employee.info)
            binding.run {
                classTitle.text = classInfo.name
                classType.text = classInfo.type.replaceFirstChar { it.uppercase() }
                if (classInfo.link.isBlank()) {
                    classPlace.text = classInfo.placeName
                    placeHolder.setOnClickListener {
                        val geo = Uri.parse("geo:${classInfo.place.latitude},${classInfo.place.longitude}?z=15")
                        val mapIntent = Intent(Intent.ACTION_VIEW, geo)
                        try {
                            startActivity(mapIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    classPlace.text = getString(R.string.online)
                    iconPlaceLink.visibility = View.VISIBLE
                    placeHolder.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(classInfo.link))
                        try {
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error link: ${classInfo.link}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                classTime.text = "${classInfo.dateWeekDay}, ${classInfo.dateDay} ${classInfo.dateMonth} from ${classInfo.startTime} to ${classInfo.endTime}"
                professorCard.setOnClickListener {
                    BottomSheetFragmentProfessorInfo.newInstance().show(childFragmentManager, "professor_info")
                }
            }
        }
    }
}

class FragmentDetailClassInfoTabDeadlines : BaseFragment<FragmentDetailClassInfoTabDeadlinesBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoTabDeadlinesBinding
        get() = FragmentDetailClassInfoTabDeadlinesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo (получать дедлайны из сети и локальные, связанные с дисциплинной)
    }
}