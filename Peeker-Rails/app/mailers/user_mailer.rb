class UserMailer < ApplicationMailer
  def password_reset
    @user = params[:user]
    @signed_id = @user.signed_id(purpose: :password_reset, expires_in: 20.minutes)

    mail to: @user.email, subject: "Reset your password"
  end

  def email_verification
    @user = params[:user]
    @user.verification_code.value = rand.to_s[2..7]

    mail to: @user.email, subject: "Verify your email"
  end
end
