class User < ApplicationRecord
  has_secure_password

  has_many :sessions, dependent: :destroy

  kredis_string :verification_code, expires_in: 2.days

  validates :email, presence: true, uniqueness: true, format: { with: URI::MailTo::EMAIL_REGEXP }
  validates :password, allow_nil: true, length: { minimum: 12 }, format: { with: /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/ }
  validates :password, not_pwned: { message: "might easily be guessed" }

  before_validation do
    self.email = email.try(:downcase).try(:strip)
  end

  before_validation if: :email_changed? do
    self.verified = false
  end

  after_update if: :password_digest_previously_changed? do
    sessions.where.not(id: Current.session).destroy_all
  end

  after_save_commit if: :email_previously_changed? do
    UserMailer.with(user: self).email_verification.deliver_later
  end
end
