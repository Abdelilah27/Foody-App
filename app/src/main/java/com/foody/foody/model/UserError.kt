/*
 *
 *  * Copyright 2021 All rights are reserved by Pi App Studio
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package com.foody.foody.model

import com.foody.foody.R


data class UserError(
    var nameError: Int = R.string.empty,
    var emailError: Int = R.string.empty,
    var passwordError: Int = R.string.empty,
    var confirmPasswordError: Int = R.string.empty,
    var checkBoxError: Boolean = false
) {
}