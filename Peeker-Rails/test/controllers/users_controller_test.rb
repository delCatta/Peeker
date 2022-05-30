require "test_helper"

class UsersControllerTest < ActionDispatch::IntegrationTest
  test "should get me" do
    get users_me_url
    assert_response :success
  end
end
