package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentPaymentAddBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.ui.ui.theme.WoowahanAccountBookTheme
import co.kr.woowahan_accountbook.presentation.viewmodel.main.setting.PaymentAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PaymentAddFragment : BaseFragment<FragmentPaymentAddBinding>() {
    override val TAG: String
        get() = PaymentAddFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_payment_add

    private var id: Int? = null
    private var name: String? = null
    private val viewModel by viewModels<PaymentAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("_ID")
            name = it.getString("PAYMENT_NAME")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initView()
        observeData()
    }

    private fun initView() {
        with(binding.tbPaymentAdd) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        name?.let { viewModel.name.value = it }
        id?.let { viewModel.id.value = it }
//        binding.btnAdd.setOnClickListener {
//            id?.let {
//                viewModel.updatePayment(it)
//            } ?: viewModel.addPayment()
//        }
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WoowahanAccountBookTheme {
                    MainScreen()
                }
            }
        }
    }

    private fun observeData() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) requireActivity().onBackPressed()
        }
    }
}

@Composable
fun MainScreen(viewModel: PaymentAddViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var text = remember { mutableStateOf(viewModel.name.value) }
    var id = remember { mutableStateOf(viewModel.id.value) }

    Surface(
        color = Color(0xFFF7F6F3),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 40.dp, start = 16.dp, end = 12.dp)
    ) {
        Box {
            Column {
                Box {
                    Row {
                        Text(
                            text = "이름",
                            fontSize = 14.sp,
                            color = Color(0xFF524D90),
                            fontWeight = FontWeight(500),
                            modifier = Modifier.weight(76f),
                            fontFamily = FontFamily(Font(resId = R.font.ko_pub_world_dotum_pro))
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(modifier = Modifier.weight(288f)) {
                            BasicTextField(
                                value = text.value ?: "",
                                onValueChange = {
                                    text.value = it
                                    viewModel.name.value = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xFF524D90),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(resId = R.font.ko_pub_world_dotum_pro))
                                ),
                                modifier = Modifier.fillMaxWidth(),
                            )
                            if (text.value == "") {
                                Text(
                                    text = "입력하세요",
                                    fontSize = 14.sp,
                                    color = Color(0xFFA79FCB),
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily(Font(resId = R.font.ko_pub_world_dotum_pro))
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Surface(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(), color = Color(0x66A79FCB)
                ) {}
            }
            Button(
                onClick = {
                    if (id.value == -1) {
                        viewModel.addPayment()
                    } else {
                        viewModel.updatePayment()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                enabled = text.value != "",
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF5B853),
                    disabledBackgroundColor = Color(0x80F5B853)
                )
            ) {
                Text(
                    text = if(id.value == -1) "등록하기" else "수정하기", color = Color(0xFFFFFFFF),
                    fontSize = 14.sp, fontWeight = FontWeight(700),
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontFamily = FontFamily(Font(resId = R.font.ko_pub_world_dotum_pro))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    WoowahanAccountBookTheme() {
        MainScreen()
    }
}