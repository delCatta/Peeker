# frozen_string_literal: true

module Identity
  class EmailVerificationsController < ApplicationController
    skip_before_action :authenticate, only: :edit

    before_action :set_user, only: :edit

    def edit
      @user.update! verified: true
    end

    def create
      UserMailer.with(user: Current.user).email_verification.deliver_later
    end

    private

    def set_user
      verified_user = User.find_by(email: params[:email])

      if verified_user && verified_user.verification_code.value == params[:token]
        @user = verified_user
      else
        render json: { error: 'That email verification code is invalid' }, status: :bad_request
      end
    end
  end
end
